package enjuu.kazuki.click;

import java.awt.Color;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import javax.security.auth.login.LoginException;

import org.json.simple.parser.ParseException;

import API.Score;
import API.User;
import API.UserType;
import Main.RippleAPI;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;

public class Main {
	
	public static JDABuilder builder = new JDABuilder(AccountType.BOT);
	public static Statement query;
	public static Connection con;
	public static JDA jda;
	public static String uname = "nothing";
	
	
	public static void main(String[] args) {
		Config.createConfig();
		SQLConfig.createConfig();
		try {
			Config.loadConfig();
			SQLConfig.loadConfig();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		RippleAPI.setServer(Config.getString("api"));
		builder.setToken(Config.getString("token"));
		builder.setGame(Game.playing(Config.getString("status")));
		builder.setAutoReconnect(true);
		builder.setStatus(OnlineStatus.ONLINE);
		
		try {
			jda = builder.buildBlocking();
		} catch (LoginException | InterruptedException e2) {
			e2.printStackTrace();
		}
		
		//SQL
		String url = "jdbc:mysql://"+SQLConfig.getString("hostname")+":"+SQLConfig.getString("port")+"/"+SQLConfig.getString("database")+ "?useLegacyDatetimeCode=false&serverTimezone=America/New_York";
		String username = SQLConfig.getString("username");
		String password = SQLConfig.getString("password");

		System.out.println("[SQL] Connecting database...");

		try {
			con = DriverManager.getConnection(url, username, password);
			query = con.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		    
		
		
		//Primary Code
		while(true) {
			try {
			String old = "";
			TimeUnit.SECONDS.sleep(3);
			String sql = "SELECT\n" + 
					" *\n" + 
					" FROM scores\n" + 
					" ORDER BY id DESC\n" + 
					" LIMIT 1;";
			ResultSet result = null;
			result = query.executeQuery(sql);
			String beatmapmd5 = null;
			result.next();
			beatmapmd5 = result.getString("beatmap_md5");
			
			if(beatmapmd5.equals(old)) {
				
			}else {
				old = beatmapmd5;
				String userid = null;
				userid = result.getString("userid");
				
				
				User u = null;
				u = new User(UserType.ID, userid);
				
				Score s = null;
				s = u.getLastScore();
				
				if(!uname.equals(u.getName())) {
					uname = u.getName();
					EmbedBuilder e = new EmbedBuilder();
					e.setColor(new Color(22, 160, 133));
					e.setAuthor(u.getName(), u.getUser_Page().toString(), "https://a."+Config.getString("api")+"/"+u.getID());
					
					
						e.setDescription("__New score! **"+s.getPP()+"pp**__\n"
								+ "▸ "+s.getMax_Combo()+"/"+s.getmap_max_combo()+" • "+s.getRank()+" • "
								+s.getaccuracy()+"%\n"
								+"▸ ["+s.getsong_name()+"](https://osu.ppy.sh/b/"+s.getbeatmap_id()+")\n"
								+ "▸ [Profile]("+u.getUser_Page().toString()+"), [Leaderboard](https://"+Config.getString("web")+"/b/"+s.getbeatmap_id()+")");
						
					e.setThumbnail("https://b.ppy.sh/thumb/"+s.getbeatmapset_id()+".jpg");
					for(Guild g : jda.getGuilds()) {
						g.getTextChannelById(Config.getString("channelid")).sendMessage(e.build()).queue();
					}
				}

				
			}
		}catch(Exception e) {
			// Call restart Script
			System.exit(0);
		}
			
		}
	

	}
}
