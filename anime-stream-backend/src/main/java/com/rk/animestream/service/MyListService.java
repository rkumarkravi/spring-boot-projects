package com.rk.animestream.service;

import com.rk.animestream.daos.AnimeRepository;
import com.rk.animestream.daos.MyListRepository;
import com.rk.animestream.daos.UserRepository;
import com.rk.animestream.dtos.AnimeDto;
import com.rk.animestream.dtos.MyListDto;
import com.rk.animestream.exceptions.ExpiredJwtTokenException;
import com.rk.animestream.mapper.AnimeMapper;
import com.rk.animestream.mapper.MyListMapper;
import com.rk.animestream.models.Anime;
import com.rk.animestream.models.MyList;
import com.rk.animestream.utils.JWTUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MyListService {
    @Autowired
    JWTUtility jwtUtility;
    @Autowired
    MyListRepository myListRepository;

    @Autowired
    MyListMapper myListMapper;

    @Autowired
    AnimeRepository animeRepository;

    @Autowired
    AnimeMapper animeMapper;

    @Autowired
    UserRepository userRepository;

    public Page<AnimeDto> getMyList(String token, Pageable pageable) throws ExpiredJwtTokenException {
        String email=jwtUtility.getUsernameFromToken(token);
        log.debug("user email: {}",email);
        Page<Anime> myList = myListRepository.findAllAnimeInMyList(email,pageable);
        return new PageImpl<AnimeDto>(myList.getContent().stream().map(x->animeMapper.animeToAnimeDto(x)).collect(Collectors.toList()));
    }

    public MyListDto addToMyList(String token, Long aid) throws ExpiredJwtTokenException {
        String email=jwtUtility.getUsernameFromToken(token);
        log.debug("user email: {}",email);
        Optional<MyList> optionMyList = myListRepository.findByUser_EmailId(email);
        Optional<Anime> anime = animeRepository.findById(aid);
        if(!anime.isPresent()){
            throw new RuntimeException("Anime with id "+aid+" Not Found!");
        }
        MyList myList;
        if(optionMyList.isPresent()) {
            myList = optionMyList.get();
        }else {
            myList = new MyList();
            myList.setUser(userRepository.findByEmailId(email).get());
        }
        myList.getAnimes().add(anime.get());

        return myListMapper.myListToMyListDto(myListRepository.save(myList));
    }

    public int removeFromMyList(String token, Long aid) throws ExpiredJwtTokenException {
        String email=jwtUtility.getUsernameFromToken(token);
        log.debug("removeFromMyList email: {}",email);
        Optional<Anime> anime = animeRepository.findById(aid);
        if(!anime.isPresent()){
            throw new RuntimeException("Anime with id "+aid+" Not Found!");
        }
        Optional<MyList> optionMyList = myListRepository.findByUser_EmailId(email);
        int removed= -1;
        if(optionMyList.isPresent()) {
            MyList myList = optionMyList.get();
            myList.getAnimes().remove(anime.get());
            myListRepository.save(myList);
            removed=1;
        }
        return removed;
    }
}
