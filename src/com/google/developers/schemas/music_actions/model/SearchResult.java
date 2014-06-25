package com.google.developers.schemas.music_actions.model;

public class SearchResult {
	
	private Long id;
	private String type;
	private String name;
	private String url;
	
	public SearchResult(Long id, String type, String name) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
