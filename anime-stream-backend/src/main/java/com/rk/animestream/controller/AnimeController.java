package com.rk.animestream.controller;

import com.rk.animestream.dtos.AnimeDto;
import com.rk.animestream.enums.ResponseStatus;
import com.rk.animestream.pojos.ApiResponse;
import com.rk.animestream.service.AnimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/anime")
@Slf4j
public class AnimeController {
    @Autowired
    AnimeService animeService;

    @PostMapping("/add")
    public ApiResponse addAnime(@RequestBody AnimeDto animeDto, HttpServletRequest httpRequest){
       ApiResponse<AnimeDto> apiResponse=new ApiResponse<>();
       apiResponse.setData(animeService.addAnime(animeDto));
       apiResponse.setMessage("Anime '"+animeDto.getName()+"' added successfully");
       apiResponse.setPath(String.valueOf(httpRequest.getRequestURI()));
       apiResponse.setStatus(ResponseStatus.SUCCESS);
       return apiResponse;
    }

    @GetMapping("/get/{aid}")
    public ApiResponse addAnime(@PathVariable Long aid, HttpServletRequest httpRequest){
        ApiResponse<AnimeDto> apiResponse=new ApiResponse<>();
        apiResponse.setData(animeService.getAnime(aid));
        apiResponse.setMessage("Anime fetch successful");
        apiResponse.setPath(String.valueOf(httpRequest.getRequestURI()));
        apiResponse.setStatus(ResponseStatus.SUCCESS);
        return apiResponse;
    }

    @PostMapping("/getAll")
    public PageImpl<AnimeDto> getAllAnime(@RequestBody Map<String,String> page){
        Integer pageNo=Integer.valueOf(page.get("page"));
        Integer pageSize=Integer.valueOf(page.get("size"));
        log.debug("page :{}, size :{}",pageNo,pageSize);
        Pageable pageable= PageRequest.of(pageNo,pageSize);
        return this.animeService.getAllAnime(pageable);
    }
}
