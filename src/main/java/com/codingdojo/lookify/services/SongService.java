package com.codingdojo.lookify.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codingdojo.lookify.models.Song;
import com.codingdojo.lookify.repositories.SongRepository;

@Service
public class SongService {

	private final SongRepository songRepository;

	public SongService(SongRepository songRepository) {
		this.songRepository = songRepository;
	}

	public void addSong(Song song) {
		songRepository.save(song);
	}

	public List<Song> findAllSong() {
		return (List<Song>) songRepository.findAll();
	}

	public Song addSong(Long myId) {
		Optional<Song> findSong = songRepository.findById(myId);
		if (findSong.isPresent()) {
			return findSong.get();
		} else {
			return null;
		}
	}

	public void deleteSong(Long id) {
		songRepository.deleteById(id);
	}

	public List<Song> getTopTen() {
		return songRepository.findTop10ByOrderByRatingDesc();
	}
	public List<Song> findbyArtist(String name){
		return songRepository.findByArtist(name);
	}
	

}
