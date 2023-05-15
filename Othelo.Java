package misc;

import java.util.ArrayList;

public class Othelo {
	
	public static class piece{
		
		//if type == 1 ---> black;if type == 0 ---> white
		private int type;
		public piece(int t){
			type = t;
		}
		public int get_type() {
			return type;
		}
		public void flip(){
			if(type == 1) {
				type = 0;
				return;
			}
			type = 1;
		}
		
	}
	
	public static class player{
		
		//can be player 0(whites) or 1(blacks)
		private int num_player;
		private int pieces_on_board = 0;
		private int pieces_on_hand = 32;
		
		
		public player(int num) {
			num_player = num;
		}
		
		public int get_pieces_on_hand() {
			return pieces_on_hand;
		}
		public int get_pieces_on_board() {
			return pieces_on_board;
		}
		public int get_num() {
			return num_player;
		}
		
		public void set_pieces_on_hand(int num){
			pieces_on_hand = num;
		}
		public void set_pieces_on_board(int num){
			pieces_on_board = num;
		}
		
	}
	
	private static ArrayList<ArrayList<piece>> board = new ArrayList<>();
	private static player player_0;
	private static player player_1;
	
	public Othelo() {
		//Starting the board:
		for(int i = 0;i < 8;i++) {
			board.add(new ArrayList<>());
		}
		for(int i = 0;i < 8;i++) {
			for(int j = 0;j < 8;j++) {
				board.get(i).add(null);
			}
		}
		//
		
		//Setting the players
		player_0 = new player(0);
		player_1 = new player(1);
		
		//the game starts with 2 whites and 2 blacks
		//whites --> (3,3);(4,4)
		//black ---> (3,4);(4,3)
		
		player_0.set_pieces_on_board(2);
		player_0.set_pieces_on_hand(player_0.get_pieces_on_hand()-2);
		player_1.set_pieces_on_board(2);
		player_1.set_pieces_on_hand(player_1.get_pieces_on_hand()-2);
		
		//adding whites:
		piece w1 = new piece(0);
		board.get(3).remove(3);
		board.get(3).add(3,w1);
		piece w2 = new piece(0);
		board.get(4).remove(4);
		board.get(4).add(4,w2);
		//adding blacks
		piece b1 = new piece(1);
		board.get(3).remove(4);
		board.get(3).add(4,b1);
		piece b2 = new piece(1);
		board.get(4).remove(3);
		board.get(4).add(3,w1);
	}
	public void add_piece(piece p,int i,int j) {
		
		if(p.get_type() == 0) {
			player_0.set_pieces_on_board(player_0.get_pieces_on_board()+1);
			player_0.set_pieces_on_hand(player_0.get_pieces_on_hand()-1);
			board.get(i).remove(j);
			board.get(i).add(j,p);
		}
		else if(p.get_type() == 1) {
			player_1.set_pieces_on_board(player_1.get_pieces_on_board()+1);
			player_1.set_pieces_on_hand(player_1.get_pieces_on_hand()-1);
			board.get(i).remove(j);
			board.get(i).add(j,p);
		}
	}
	
	//after a player add a piece i have to check the conditions to flip
	
	public void flip_all(ArrayList<piece> all){
		
		for(int i = 0;i < all.size();i++) {
			piece x = all.get(i);
			x.flip();
		}
		
	}
	
	public boolean check_filp(player player,int m,int n){
		//check if wich pieces the player will flip,if the player cant capture at least 1 piece the game ends
		//and the player with the most pieces on board wins
		
		ArrayList<piece> temp = new ArrayList<>();//store the pieces that will be flipped 
		
		int total = 0;
		//checking left and right
		//(m,n) ---> (m,8)
		int c = 0;
		for(int i = n;i < 8;i++) {
			piece p = board.get(m).get(i);
			c++;
			temp.add(p);
			if(p.get_type() == player.get_num()) {
				break;
			}
			if(i == 7) {
				//no flank
				c = 0;
			}
		}
		if(c != 0) {
			flip_all(temp);
			temp.clear();
		}
		total += c;
		//(m,n) --> (m,0)
				c = 0;
				for(int i = n-1;i >= 0;i--) {
					piece p = board.get(m).get(i);
					c++;
					temp.add(p);
					if(p.get_type() == player.get_num()) {
						break;
					}
					if(i == 0) {
						//no flank
						c = 0;
					}
				}
				if(c != 0) {
					flip_all(temp);
					temp.clear();
				}
				total += c;
		//check up and down
		//(m,n) --> (8,n)
		c = 0;
		for(int i = m;i < 8;i++) {
			piece p = board.get(i).get(n);
			c++;
			temp.add(p);
			if(p.get_type() == player.get_num()) {
				break;
			}
			if(i == 7){
				c = 0;
			}
		}
		if(c != 0) {
			flip_all(temp);
			temp.clear();
		}
		total += c;
		//(0,n) --> (0,n)
				c = 0;
				for(int i = m-1;i >= 0;i--) {
					piece p = board.get(i).get(n);
					c++;
					temp.add(p);
					if(p.get_type() == player.get_num()) {
						break;
					}
					if(i == 7){
						c = 0;
					}
				}
				if(c != 0) {
					flip_all(temp);
					temp.clear();
				}
		 total += c;
		
		if(total == 0) {
			//game ends
			
			//compute winner
			if(player_0.get_pieces_on_board() > player_1.get_pieces_on_board()) {
				System.out.println("Player 0 won!");
			}
			else if(player_1.get_pieces_on_board() > player_0.get_pieces_on_board()) {
				System.out.println("Player 1 won!");
			}
			
			return false;
		}
		//game continues
		return true;
	}

}
