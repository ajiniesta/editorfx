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
 * File created: 02/02/2013 at 22:56:05 by antonio
 */
package com.iniesta.editorfx.editor.files.business;

import java.io.File;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author antonio
 *
 */
public class HelperEditorFiles {

	/**
	 * Get the file from a file chooser
	 * @param stage
	 * @return
	 */
	public static File getFile(Stage stage){
		File folder = null;
		FileChooser fc = new FileChooser();
		fc.setTitle("Load File");
		folder = fc.showOpenDialog(stage);
		return folder;
	}

	/**
	 * Get the folder from a file chooser
	 * @return
	 */
	public static File getFolder(Stage stage){
		File folder = null;
		DirectoryChooser dc = new DirectoryChooser();
		dc.setTitle("Load folder");
		folder = dc.showDialog(stage);
		return folder;
	}

	public static File getSaveFile() {
		File file = null;
		FileChooser fc = new FileChooser();
		fc.setTitle("Save File");
		file = fc.showSaveDialog(null);
		return file;
	}
	
}
