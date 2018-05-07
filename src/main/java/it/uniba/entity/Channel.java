package it.uniba.entity;

import java.util.LinkedList;
import java.util.List;

public class Channel {
	private String id;
	private String name;
	private List<Member> members;
	private List<Mention> mentions;
	
	public Channel(String id, String name) {
		this.id=id;
		this.name=name;
		this.members=new LinkedList<Member>();
		this.mentions=new LinkedList<Mention>();
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public LinkedList<Member> getMembers(){
		return (LinkedList<Member>) this.members;
	}
	
	public LinkedList<Mention> getMentions(){
		return (LinkedList<Mention>) this.mentions;
	}
}
