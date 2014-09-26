package com.iniesta.editorfx.editor.files.container;

import com.iniesta.editorfx.editor.AceEditor;
import com.iniesta.editorfx.editor.UpdateNotification;

public class FactoryContainer {

	private static FactoryContainer instance;

	private FactoryContainer(){
		super();
	}
	
	public static FactoryContainer getInstance(){
		if(instance==null){
			instance = new FactoryContainer();
		}
		return instance;
	}
	
	public Container getContainer(String fileName, UpdateNotification notification){
		Container container = null;
		if(fileName!=null){
			if(fileName.endsWith(".csv")){
				container = new CSVContainer(notification);
			}else{
				container = new AceEditor(notification);
			}
		}
		return container;
	}
}
