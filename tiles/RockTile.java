package tiles;

import graphics.Assets;

public class RockTile extends Tile{
	
	public RockTile(int id){
		super(Assets.stone, id);
		isSolid = true;
	}

	@Override
	public boolean isSolid(){
		return isSolid;
	}
	
}
