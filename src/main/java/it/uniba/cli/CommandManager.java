package it.uniba.cli;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.ListIterator;

import it.uniba.entity.Channel;
import it.uniba.entity.ChannelNotValidException;
import it.uniba.entity.Member;
import it.uniba.entity.MemberNotValidException;
import it.uniba.entity.Mention;
import it.uniba.entity.Workspace;
import it.uniba.file.PathManager;
import it.uniba.file.zip.FileNotInZipException;
import it.uniba.file.zip.NotValidWorkspaceException;
import it.uniba.file.zip.NotZipFileException;

public class CommandManager {
	public static void help() {
		Commands commands = new Commands();
		int maxNumCharCommand = 0;
		int maxNumCharDescription = 0;
		ListIterator<Command> commandsIterator = (ListIterator<Command>) commands.getCommands().iterator();
		while (commandsIterator.hasNext()) {
			Command curr = commandsIterator.next();
			if ((curr.getName() + " " + curr.getOptions()).length() >= maxNumCharCommand) {
				maxNumCharCommand = (curr.getName() + " " + curr.getOptions()).length();
			}
			if (curr.getDescription().length() >= maxNumCharDescription) {
				maxNumCharDescription = curr.getDescription().length();
			}
		}
		System.out.format("%" + maxNumCharCommand + "s\t%" + maxNumCharDescription + "s", "COMMAND", "DESCRIPTION");
		System.out.println();
		System.out.println();
		commandsIterator = (ListIterator<Command>) commands.getCommands().iterator();
		while (commandsIterator.hasNext()) {
			Command curr = commandsIterator.next();
			System.out.format("%" + maxNumCharCommand + "s\t%" + maxNumCharDescription + "s",
					curr.getName() + " " + curr.getOptions(), curr.getDescription());
			System.out.println();
		}
	}

	public static void getChannels(String workspace) {
		try {
			Workspace slackWorkspace = new Workspace(PathManager.getAbsolutePath(workspace));
			LinkedHashMap<String, Channel> workspaceChannels = slackWorkspace.getAllChannels();
			Collection<Channel> c = workspaceChannels.values();
			Iterator<Channel> channelsIterator = c.iterator();
			while (channelsIterator.hasNext()) {
				System.out.println(channelsIterator.next().getName());
			}
		} catch (IOException e) {
			if (e instanceof FileNotFoundException || e instanceof NoSuchFileException) {
				System.out.println(PathManager.getAbsolutePath(workspace) + " not found");
			} else {
				e.printStackTrace();
			}
		} catch (FileNotInZipException | NotValidWorkspaceException | NotZipFileException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void getMembers(String workspace) {
		try {
			Workspace slackWorkspace = new Workspace(PathManager.getAbsolutePath(workspace));
			LinkedHashMap<String, Member> workspaceMembers = slackWorkspace.getAllMembers();
			Collection<Member> c = workspaceMembers.values();
			Iterator<Member> membersIterator = c.iterator();
			while (membersIterator.hasNext()) {
				System.out.println(membersIterator.next().getName());
			}
		} catch (IOException e) {
			if (e instanceof FileNotFoundException || e instanceof NoSuchFileException) {
				System.out.println(PathManager.getAbsolutePath(workspace) + " not found");
			} else {
				e.printStackTrace();
			}
		} catch (FileNotInZipException | NotValidWorkspaceException | NotZipFileException e) {
			System.out.println(e.getMessage());

		}
	}

	public static void getMembersOfChannel(String workspace, String channel) {
		try {
			Workspace slackWorkspace = new Workspace(PathManager.getAbsolutePath(workspace));
			LinkedList<Member> channelMembers = slackWorkspace.getMembersOfChannel(channel);
			ListIterator<Member> membersIterator = (ListIterator<Member>) channelMembers.iterator();
			while (membersIterator.hasNext()) {
				System.out.println(membersIterator.next().getName());
			}
		} catch (IOException e) {
			if (e instanceof FileNotFoundException || e instanceof NoSuchFileException) {
				System.out.println(PathManager.getAbsolutePath(workspace) + " not found");
			} else {
				e.printStackTrace();
			}
		} catch (ChannelNotValidException | FileNotInZipException | NotValidWorkspaceException
				| NotZipFileException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void getMembersForChannels(String workspace) {
		try {
			Workspace slackWorkspace = new Workspace(PathManager.getAbsolutePath(workspace));
			LinkedHashMap<String, Channel> workspaceChannels = slackWorkspace.getAllChannels();
			Collection<Channel> c = workspaceChannels.values();
			Iterator<Channel> channelsIterator = c.iterator();
			while (channelsIterator.hasNext()) {
				Channel curr = channelsIterator.next();
				LinkedList<Member> channelMembers = slackWorkspace.getMembersOfChannel(curr.getName());
				ListIterator<Member> membersIterator = (ListIterator<Member>) channelMembers.iterator();
				System.out.println("Members of \"" + curr.getName() + "\":");
				while (membersIterator.hasNext()) {
					System.out.println("\t" + membersIterator.next().getName());
				}
				if (channelsIterator.hasNext()) {
					System.out.println();
				}
			}
		} catch (IOException e) {
			if (e instanceof FileNotFoundException || e instanceof NoSuchFileException) {
				System.out.println(PathManager.getAbsolutePath(workspace) + " not found");
			} else {
				e.printStackTrace();
			}
		} catch (FileNotInZipException | NotValidWorkspaceException | NotZipFileException e) {
			System.out.println(e.getMessage());
		} catch (ChannelNotValidException e) {
			e.printStackTrace();
		}
	}

	public static void getMentionsFromUser(String workspace, String member) {
		try {
			Workspace slackWorkspace = new Workspace(PathManager.getAbsolutePath(workspace));
			Collection<Channel> coll_Channel = slackWorkspace.getAllChannels().values();
			Iterator<Channel> it_channel = coll_Channel.iterator();
			while (it_channel.hasNext()) {
				LinkedList<Mention> l_mention = slackWorkspace.getMentionsFromUser(it_channel.next().getName(), member);
				ListIterator<Mention> it = (ListIterator<Mention>) l_mention.iterator();
				while (it.hasNext()) {
					Mention m = it.next();
					System.out.println(m);
				}
			}
		} catch (IOException | ChannelNotValidException e) {
			if (e instanceof FileNotFoundException || e instanceof NoSuchFileException) {
				System.out.println(PathManager.getAbsolutePath(workspace) + " not found");
			} else {
				e.printStackTrace();
			}
		} catch (MemberNotValidException | NotValidWorkspaceException | FileNotInZipException | NotZipFileException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void getMentionsFromUser(String workspace, String channel, String member) {
		try {
			Workspace slackWorkspace = new Workspace(PathManager.getAbsolutePath(workspace));
			LinkedList<Mention> l_mention = slackWorkspace.getMentionsFromUser(channel, member);
			ListIterator<Mention> it = (ListIterator<Mention>) l_mention.iterator();
			while (it.hasNext()) {
				Mention m = it.next();
				System.out.println(m);
			}
		} catch (IOException e) {
			if (e instanceof FileNotFoundException || e instanceof NoSuchFileException) {
				System.out.println(PathManager.getAbsolutePath(workspace) + " not found");
			} else {
				e.printStackTrace();
			}
		} catch (ChannelNotValidException | MemberNotValidException | NotValidWorkspaceException | FileNotInZipException
				| NotZipFileException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void getMentionsToUser(String workspace, String member) {
		try {
			Workspace slackWorkspace = new Workspace(PathManager.getAbsolutePath(workspace));
			Collection<Channel> coll_Channel = slackWorkspace.getAllChannels().values();
			Iterator<Channel> it_channel = coll_Channel.iterator();
			while (it_channel.hasNext()) {
				LinkedList<Mention> l_mention = slackWorkspace.getMentionsToUser(it_channel.next().getName(), member);
				ListIterator<Mention> it = (ListIterator<Mention>) l_mention.iterator();
				while (it.hasNext()) {
					Mention m = it.next();
					System.out.println(m);
				}
			}
		} catch (IOException | ChannelNotValidException e) {
			if (e instanceof FileNotFoundException || e instanceof NoSuchFileException) {
				System.out.println(PathManager.getAbsolutePath(workspace) + " not found");
			} else {
				e.printStackTrace();
			}
		} catch (MemberNotValidException | NotValidWorkspaceException | FileNotInZipException | NotZipFileException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void getMentionsToUser(String workspace, String channel, String member) {
		try {
			Workspace slackWorkspace = new Workspace(PathManager.getAbsolutePath(workspace));
			LinkedList<Mention> l_mention = slackWorkspace.getMentionsToUser(channel, member);
			ListIterator<Mention> it = (ListIterator<Mention>) l_mention.iterator();
			while (it.hasNext()) {
				Mention m = it.next();
				System.out.println(m);
			}
		} catch (IOException e) {
			if (e instanceof FileNotFoundException || e instanceof NoSuchFileException) {
				System.out.println(PathManager.getAbsolutePath(workspace) + " not found");
			} else {
				e.printStackTrace();
			}
		} catch (ChannelNotValidException | MemberNotValidException | NotValidWorkspaceException | FileNotInZipException
				| NotZipFileException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void getMentions(String workspace, String channel) {
		try {
			Workspace slackWorkspace = new Workspace(PathManager.getAbsolutePath(workspace));
			LinkedList<Mention> workspaceMentions = slackWorkspace.getMentions(channel);
			Iterator<Mention> mentionsIterator = workspaceMentions.iterator();
			while (mentionsIterator.hasNext()) {
				Mention curr = mentionsIterator.next();
				System.out.println(curr);
			}
		} catch (IOException e) {
			if (e instanceof FileNotFoundException || e instanceof NoSuchFileException) {
				System.out.println(PathManager.getAbsolutePath(workspace) + " not found");
			} else {
				e.printStackTrace();
			}
		} catch (ChannelNotValidException | FileNotInZipException | NotValidWorkspaceException
				| NotZipFileException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void getMentions(String workspace) {
		try {
			Workspace slackWorkspace = new Workspace(PathManager.getAbsolutePath(workspace));
			LinkedHashMap<String, Channel> workspaceChannels = slackWorkspace.getAllChannels();
			Collection<Channel> c = workspaceChannels.values();
			Iterator<Channel> channelsIterator = c.iterator();
			while (channelsIterator.hasNext()) {
				Channel currchannel = channelsIterator.next();
				LinkedList<Mention> workspaceMentions = slackWorkspace.getMentions(currchannel.getName());
				Iterator<Mention> mentionsIterator = workspaceMentions.iterator();
				while (mentionsIterator.hasNext()) {
					Mention currmention = mentionsIterator.next();
					System.out.println(currmention);
				}
			}
		} catch (IOException e) {
			if (e instanceof FileNotFoundException || e instanceof NoSuchFileException) {
				System.out.println(PathManager.getAbsolutePath(workspace) + " not found");
			} else {
				e.printStackTrace();
			}
		} catch (FileNotInZipException | NotValidWorkspaceException | NotZipFileException e) {
			System.out.println(e.getMessage());
		} catch (ChannelNotValidException e) {
			e.printStackTrace();
		}
	}

	public static void Gestione(String[] args) {
		switch (args.length) {
		case 0:
			CommandManager.help();
			break;
		case 1:
			if (args[0].equals("help"))
				CommandManager.help();
			else
				System.out.println("'" + args[0] + "'" + " is not a valid command, see 'help'.");
			break;
		case 2:
			System.out.println("'" + args[0] + " " + args[1] + "'" + " is not a valid command, see 'help'.");
			break;
		case 3:
			if (args[0].equals("members") && args[1].equals("-f"))
				CommandManager.getMembers(args[2]);
			else if (args[0].equals("channels") && args[1].equals("-f"))
				CommandManager.getChannels(args[2]);
			else if (args[0].equals("mentions") && args[1].equals("-f"))
				CommandManager.getMentions(args[2]);
			else
				System.out.println(
						"'" + args[0] + " " + args[1] + " " + args[2] + "'" + " is not a valid command, see 'help'.");
			break;
		case 4:
			if (args[0].equals("members") && args[1].equals("-ch") && args[2].equals("-f"))
				CommandManager.getMembersForChannels(args[3]);
			else
				System.out.println("'" + args[0] + " " + args[1] + " " + args[2] + " " + args[3] + "'"
						+ " is not a valid command, see 'help'.");
			break;
		case 5:
			if (args[0].equals("members") && args[1].equals("-ch") && args[3].equals("-f"))
				CommandManager.getMembersOfChannel(args[4], args[2]);
			else if (args[0].equals("mentions") && args[1].equals("-ch") && args[3].equals("-f"))
				CommandManager.getMentions(args[4], args[2]);
			else if (args[0].equals("mentions") && args[1].equals("-to") && args[3].equals("-f"))
				CommandManager.getMentionsToUser(args[4], args[2]);
			else if (args[0].equals("mentions") && args[1].equals("-from") && args[3].equals("-f"))
				CommandManager.getMentionsFromUser(args[4], args[2]);
			else
				System.out.println("'" + args[0] + " " + args[1] + " " + args[2] + " " + args[3] + " " + args[4] + "'"
						+ " is not a valid command, see 'help'.");
			break;
		case 7:
			if (args[0].equals("mentions") && args[1].equals("-to") && args[3].equals("-ch") && args[5].equals("-f"))
				CommandManager.getMentionsToUser(args[6], args[4], args[2]);
			else if (args[0].equals("mentions") && args[1].equals("-from") && args[3].equals("-ch")
					&& args[5].equals("-f"))
				CommandManager.getMentionsFromUser(args[6], args[4], args[2]);
			else
				System.out.println("'" + args[0] + " " + args[1] + " " + args[2] + " " + args[3] + " " + args[4] + " "
						+ args[5] + " " + args[6] + "'" + " is not a valid command, see 'help'.");
			break;
		default:
			System.out.println("Command not found, see 'help'.");
			break;
		}
	}
}
