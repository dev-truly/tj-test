package com.example.team.controller.admin;

import com.example.team.service.AttachService;
import com.example.team.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/attach")
public class AttachController {
    @Autowired
    AttachService attachService;
    @Autowired
    BoardService boardService;

    @ResponseBody
    @DeleteMapping("/{boardNo}/{attachNo}/delete")
    public Map<String, Boolean> deleteAttach(
            @PathVariable("attachNo") Long attachNo,
            @PathVariable("boardNo") Long boardNo
    ) {
        Map<String, Boolean> result = new HashMap<>();
        attachService.deleteAttach(attachNo);
        int minusResult = boardService.attachMinus(boardNo);

        result.put("result", (minusResult > 0) ? true : false);
        return result;
    }
}
