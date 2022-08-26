package com.rk.animestream.controller;

import com.rk.animestream.dtos.AnimeDto;
import com.rk.animestream.dtos.MyListDto;
import com.rk.animestream.enums.ResponseStatus;
import com.rk.animestream.exceptions.ExpiredJwtTokenException;
import com.rk.animestream.pojos.ApiResponse;
import com.rk.animestream.service.MyListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {
   @Autowired
   MyListService myListService;

    @PostMapping("/myList")
    public ApiResponse getAnimeBySeason(@RequestHeader("Authorization") String token,HttpServletRequest httpRequest,@RequestBody Map<String,String> page) throws ExpiredJwtTokenException {

        Integer pageNo=Integer.valueOf(page.get("page"));
        Integer pageSize=Integer.valueOf(page.get("size"));
        log.debug("page :{}, size :{}",pageNo,pageSize);
        Pageable pageable= PageRequest.of(pageNo,pageSize);

        ApiResponse<Page<AnimeDto>> apiResponse=new ApiResponse<>();
        apiResponse.setData(myListService.getMyList(token.substring(7),pageable));
        apiResponse.setMessage("");
        apiResponse.setPath(String.valueOf(httpRequest.getRequestURI()));
        apiResponse.setStatus(ResponseStatus.SUCCESS);
        return apiResponse;
    }

    @PutMapping("/myList/add/{aid}")
    public ApiResponse addToMyList(@PathVariable Long aid,@RequestHeader("Authorization") String token,HttpServletRequest httpRequest) throws ExpiredJwtTokenException {
        ApiResponse<MyListDto> apiResponse=new ApiResponse<>();
        apiResponse.setData(myListService.addToMyList(token.substring(7),aid));
        apiResponse.setMessage("");
        apiResponse.setPath(String.valueOf(httpRequest.getRequestURI()));
        apiResponse.setStatus(ResponseStatus.SUCCESS);
        return apiResponse;
    }

    @DeleteMapping("/myList/remove/{aid}")
    public ResponseEntity<ApiResponse> removeFromMyList(@PathVariable Long aid, @RequestHeader("Authorization") String token, HttpServletRequest httpRequest) throws ExpiredJwtTokenException {
        ApiResponse<Integer> apiResponse=new ApiResponse<>();
        apiResponse.setData(myListService.removeFromMyList(token.substring(7),aid));
        apiResponse.setMessage("");
        apiResponse.setPath(String.valueOf(httpRequest.getRequestURI()));
        apiResponse.setStatus(apiResponse.getData()!=-1?ResponseStatus.SUCCESS:ResponseStatus.FAIL);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
}
