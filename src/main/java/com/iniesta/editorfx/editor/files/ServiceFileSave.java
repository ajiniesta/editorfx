/** 
 * Copyright [2013] Antonio J. Iniesta
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * 
 * File created: 03/02/2013 at 15:22:36 by antonio
 */
package com.iniesta.editorfx.editor.files;

import java.io.File;
import java.io.FileWriter;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * @author antonio
 * 
 */
public class ServiceFileSave extends Service<Boolean> {

	private String text;
	private File file;
	
	public ServiceFileSave(File file, String text){
		this.file = file;
		this.text = text;
	}
	
	/* (non-Javadoc)
	 * @see javafx.concurrent.Service#createTask()
	 */
	@Override
	protected Task<Boolean> createTask() {		
		return new Task<Boolean>() {			
			@Override
			protected Boolean call() throws Exception {
				Boolean ok = false;
				if(file!=null && text!=null){
//					if(file.canWrite()){
						FileWriter fw = null;
						try{
							fw = new FileWriter(file);
							fw.write(text);
							ok = true;
						} catch(Exception ex){
							updateMessage("Error during the save of the file");							
						} finally{
							if(fw!=null){fw.close();}
						}
//					}
				}else{
					updateMessage("Nothing to save");
				}
				return ok;
			}
		};
	}

}
