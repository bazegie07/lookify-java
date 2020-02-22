package com.codingdojo.lookify.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.codingdojo.lookify.models.Song;
import com.codingdojo.lookify.services.SongService;

@Controller
public class MainController {
	private final SongService songService;
	
	public MainController(SongService songService) {
		this.songService = songService;
	}
	
	@RequestMapping("/")
	public String home() {
		return "index.jsp";
	}
	
	@RequestMapping("/dashboard")
	public String dashboard(Model model) {
		List<Song> listofSongs = songService.findAllSong();
		model.addAttribute("songs", listofSongs);
		System.out.println("---- Songs ---- : "+listofSongs);
		return "dashboard.jsp";
	}
	@RequestMapping("/songs/new")
	public String renderAddSong(@ModelAttribute("addsong")Song song) {
		return "addsong.jsp";
	}
	@RequestMapping(value="/songs/new", method=RequestMethod.POST)
	public String createSong(@Valid @ModelAttribute("addsong")Song song,BindingResult result) {
		if(result.hasErrors()) {
			return "addsong.jsp";
		}else {
			songService.addSong(song);
			return "redirect:/dashboard";
		}
			
	}
	@RequestMapping("/songs/{id}")
	public String showSong(Model model,@PathVariable("id")Long myId) {
		Song mySong = songService.addSong(myId);
		model.addAttribute("song", mySong);
		return "showsong.jsp";
	}
	@RequestMapping("/delete/{id}")
	public String deleteSong(@PathVariable("id")Long id) {
		songService.deleteSong(id);
		return "redirect:/dashboard";
	}
	@RequestMapping("/search/topTen")
	public String topTenSong(Model model) {
		List<Song> top10Songs=songService.getTopTen();
		model.addAttribute("songs", top10Songs);
		System.out.println("---top 10 --- " + top10Songs);
		return "top10.jsp";
	}
	@PostMapping("/search")
	public String searchArtist(@RequestParam("artist")String artist) {
		return "redirect:/search/"+artist;
	}
	
	@RequestMapping("/search/{artist}")
	public String showSearch(Model model,@PathVariable("artist")String artist) {
		List<Song> songs = songService.findbyArtist(artist);
		model.addAttribute("songs",songs);
		model.addAttribute("artist",artist);
		return "search.jsp";
	}
	
}


