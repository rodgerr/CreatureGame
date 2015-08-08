package map.templates;

import java.util.HashMap;

import accessory.CLog;
import accessory.cres.CResNode;

import map.MapObject;
import map.blocks.InterstateTeleporter;

public class InterstateTeleporterTemplate extends BaseTemplate {

	@Override
	public MapObject create(CResNode arg0)  {
		
		HashMap<String,String> args = arg0.getNodes();
		CLog.info(this.getClass().getSimpleName()+" creating object based on"+args);
		return new InterstateTeleporter();
	}

}
