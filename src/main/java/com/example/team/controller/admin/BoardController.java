package com.example.team.controller.admin;

import com.example.team.common.Util;
import com.example.team.entity.BoardAttach;
import com.example.team.entity.BoardEntity;
import com.example.team.service.AttachService;
import com.example.team.service.BoardService;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.*;

@Controller
@RequestMapping(path = {"/admin/board"})
public class BoardController {
    @Autowired
    BoardService boardService;
    @Autowired
    AttachService attachService;

    @GetMapping("/{boardCode}/list")
    public String getList(
            @RequestParam(name = "pageSize", required = true, defaultValue = "10") int pageSize,
            @RequestParam(name = "pageNo", required = true, defaultValue = "1") int pageNo,
            @RequestParam(name = "searchValue", required = true, defaultValue = "") String searchValue,
            @RequestParam(name = "searchType", required = true, defaultValue = "title") String searchType,
            @PathVariable(name = "boardCode") String boardCode,
            Model model
    ) {
        int totalBoardCode = boardService.boardListCount(boardCode, searchType, searchValue);
        List<BoardEntity> boardList = boardService.findBoardByPage(pageNo - 1, pageSize, boardCode, searchType, searchValue);

        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("searchType", searchType);
        model.addAttribute("totalCount", totalBoardCode);
        model.addAttribute("boardNumber", totalBoardCode - (pageSize * (pageNo - 1)));
        model.addAttribute("boardList", boardList);

        return "admin/board/list";
    }

    @GetMapping("/{boardCode}/{boardNo}/view")
    public String getView(
        @RequestParam(name = "pageSize", required = true, defaultValue = "10") int pageSize,
        @RequestParam(name = "pageNo", required = true, defaultValue = "1") int pageNo,
        @RequestParam(name = "searchValue", required = true, defaultValue = "") String searchValue,
        @RequestParam(name = "searchType", required = true, defaultValue = "title") String searchType,
        @PathVariable(name = "boardCode") String boardCode,
        @PathVariable(name = "boardNo") Long boardNo,
        Model model
    ) {
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("searchType", searchType);
        model.addAttribute("board", boardService.findBoardDetail(boardNo));

        return "admin/board/view";
    }

    @GetMapping("/{boardCode}/{boardNo}/modify")
    public String getModify(
            @RequestParam(name = "pageSize", required = true, defaultValue = "10") int pageSize,
            @RequestParam(name = "pageNo", required = true, defaultValue = "1") int pageNo,
            @RequestParam(name = "searchValue", required = true, defaultValue = "") String searchValue,
            @RequestParam(name = "searchType", required = true, defaultValue = "title") String searchType,
            @PathVariable(name = "boardCode") String boardCode,
            @PathVariable(name = "boardNo") Long boardNo,
            Model model
    ) {
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("searchType", searchType);
        model.addAttribute("board", boardService.findBoardDetail(boardNo));

        return "admin/board/modify";
    }

    @ResponseBody
    @PostMapping("/{boardCode}/{boardNo}/modify")
    public Map<String, Object> postModify(
            BoardEntity board,
            MultipartHttpServletRequest req
    ) {

        Map<String, Object> result = new HashMap<>();
        boolean reaultFlag = false;
        List<MultipartFile> fileList = req.getFiles("attach");
        ArrayList<BoardAttach> attachments = new ArrayList<>();
        attachments.addAll(attachService.findByBoardNO(board.getBoardNo()));
        try {
            for (MultipartFile attach: fileList) {
                if (attach != null && !attach.isEmpty()) {

                    //파일 저장
                    String savedFileName = Util.makeUniqueFileName(attach.getOriginalFilename()); //고유 파일명 만들기
                    String realPath = req.getServletContext().getRealPath("/board-upload/");

                    File makeFolder = new File(realPath);
                    if (!makeFolder.exists()) {
                        makeFolder.mkdir();
                    }
                    String uploadPath = realPath;

                    File file = new File(uploadPath, savedFileName);
                    if (Util.checkImageType(file)) {
                        FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + savedFileName));
                        Thumbnailator.createThumbnail(attach.getInputStream(), thumbnail, 300, 300);
                    }

                    attach.transferTo(file); //파일 저장하기

                    //데이터베이스에 저장할 데이터로 VO 객체 만들기
                    BoardAttach attachment = new BoardAttach();
                    attachment.setServerFileName(savedFileName);
                    attachment.setOriFileName(attach.getOriginalFilename());
                    attachment.setBoardNo(board.getBoardNo());

                    attachments.add(attachment);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        board.setAttachCnt(board.getAttachCnt() + attachments.size());
        board.setBoardAttaches(attachments);
        System.out.println(board);
        BoardEntity insertBoard = boardService.saveBoard(board);
        if (insertBoard != null) reaultFlag = true;

        result.put("result", reaultFlag);

        return result;
    }

    @ResponseBody
    @DeleteMapping("/delete/{boardNo}")
    public Map<String, Object> delete(
            @PathVariable("boardNo") Long boardNo
    ) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", (boardService.deleteBoard(boardNo) > 0) ? true : false);
        return result;
    }

    @GetMapping("/{boardCode}/write")
    public String getWrite(
        @RequestParam(name = "pageSize", required = true, defaultValue = "10") int pageSize,
        @RequestParam(name = "pageNo", required = true, defaultValue = "1") int pageNo,
        @RequestParam(name = "searchValue", required = true, defaultValue = "") String searchValue,
        @RequestParam(name = "searchType", required = true, defaultValue = "title") String searchType,
        @PathVariable(name = "boardCode") String boardCode,
        Model model
    ) {
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("searchType", searchType);
        model.addAttribute("boardCode", boardCode);

        return "admin/board/write";
    }

    @ResponseBody
    @PostMapping("/{boardCode}/write")
    public Map<String, Object> postWrite(
            BoardEntity board,
            MultipartHttpServletRequest req
    ) {
        board.setRef(boardService.findMaxRefbyBoardCode(board.getBoardCode()) + 1);
        //board.setRef();
        Map<String, Object> result = new HashMap<>();
        boolean reaultFlag = false;
        List<MultipartFile> fileList = req.getFiles("attach");
        ArrayList<BoardAttach> attachments = new ArrayList<>();
        try {
            for (MultipartFile attach: fileList) {
                if (attach != null && !attach.isEmpty()) {

                    //파일 저장
                    String savedFileName = Util.makeUniqueFileName(attach.getOriginalFilename()); //고유 파일명 만들기
                    String realPath = req.getServletContext().getRealPath("/board-upload/");

                    File makeFolder = new File(realPath);
                    if (!makeFolder.exists()) {
                        makeFolder.mkdir();
                    }
                    String uploadPath = realPath;

                    File file = new File(uploadPath, savedFileName);
                    if (Util.checkImageType(file)) {
                        FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + savedFileName));
                        Thumbnailator.createThumbnail(attach.getInputStream(), thumbnail, 300, 300);
                    }

                    attach.transferTo(file); //파일 저장하기

                    //데이터베이스에 저장할 데이터로 VO 객체 만들기
                    BoardAttach attachment = new BoardAttach();
                    attachment.setServerFileName(savedFileName);
                    attachment.setOriFileName(attach.getOriginalFilename());

                    attachments.add(attachment);

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        board.setAttachCnt(attachments.size());
        board.setBoardAttaches(attachments);
        BoardEntity insertBoard = boardService.saveBoard(board);
        if (insertBoard != null) reaultFlag = true;

        result.put("result", reaultFlag);

        return result;
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName, HttpServletRequest req) {
        String realPath = req.getServletContext().getRealPath("/board-upload/");
        ResponseEntity<byte[]> result = null;

        try {
            String srcFileName = URLDecoder.decode(fileName, "UTF-8");
            //log.info("filename : "+srcFileName);
            File file = new File(realPath + File.separator + srcFileName);
            //log.info("file : "+file);
            HttpHeaders header = new HttpHeaders();

            //MIME 타입 처리
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            //File객체를 Path로 변환하여 MIME 타입을 판단하여 HTTPHeaders의 Content-Type에  값으로 들어갑니다.

            //파일 데이터 처리 *FileCopyUtils.copy 아래에 정리
            //new ResponseEntity(body,header,status)
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);

        } catch (Exception e) {
            //log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}
