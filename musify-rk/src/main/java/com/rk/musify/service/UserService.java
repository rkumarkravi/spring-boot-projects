package com.rk.musify.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rk.musify.model.dao.Playlist;
import com.rk.musify.model.dao.User;
import com.rk.musify.repository.PlayListDao;
import com.rk.musify.repository.UserDao;


@Service
public class UserService {
	@Autowired
	UserDao userDao;
	@Autowired
	PlayListDao playListDao;

	public User createUser(User user) {
		Playlist playlist=new Playlist("Liked Songs");
		playListDao.save(playlist);
		user.getPlaylists().add(playlist);
		return userDao.save(user);
	}
	
	public Set<Playlist> getAllPlayList(Integer uid) {
		Set<Playlist> playlist = userDao.findById(uid).get().getPlaylists();
		return playlist;
	}

	public Map<String,Object> validUser(Map<String, String> uData) {
		Map<String,Object> retData=new HashMap<>();
		List<User> u=userDao.findByEmail(uData.get("email"));
		List<User> afterValidate=u.stream().filter(x->x.getPass().equals(uData.get("pass"))).collect(Collectors.toList());
		if(u.size()>0 ) {
			if(afterValidate.size()>0) {
				retData.put("validation", VALIDATIONS.SUCCESS);
				retData.put("userinfo", u.get(0));
			}else {
				retData.put("validation", VALIDATIONS.FAILED);
			}
		}else {
			retData.put("validation", VALIDATIONS.NOTFOUND);
		}
		return retData;
	}
}

enum VALIDATIONS{
	SUCCESS,FAILED,NOTFOUND
}
