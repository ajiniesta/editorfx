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
 * File created: 03/02/2013 at 12:15:20 by antonio
 */
package com.iniesta.editorfx.editor.files;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import com.iniesta.editorfx.editor.UpdateNotification;

/**
 * @author antonio
 * Handle all the tabs that can be in the tap pane
 */
public class TabFiles {

	private static int counter = 1;

	private static TabFiles instance;

	private TabPane paneFiles;

	private UpdateNotification updateNotification;
	
	private Map<Tab, TabFile> files;
	
	private ChangeNameListener changeTabNameListener;
	
	private TabFiles(TabPane tabPaneFiles, UpdateNotification updateNotification){
		super();
		this.paneFiles = tabPaneFiles;
		this.updateNotification = updateNotification;
		files = new HashMap<Tab, TabFile>();
		
//		imageResourceClose = new Image("/com/iniesta/editorfx/images/close.png");
		
		changeTabNameListener = new ChangeNameListener() {			
			public void change(boolean changed, TabFile tabFile, String oldName, String newName) {
				Tab tab = paneFiles.getSelectionModel().getSelectedItem();
				if(changed && tab!=null){
					files.remove(tab);
					tab.setText(newName);
					files.put(tab, tabFile);
				}
			}
		};
	}
	
	public static TabFiles getInstance(TabPane paneFiles, UpdateNotification updateNotification){
		if(instance==null){
			instance = new TabFiles(paneFiles, updateNotification);
		}
		return instance;
	}
	
	/**
	 * Open a tab witht he file specified
	 * @param paneFiles
	 * @param progress
	 * @param selectedFile
	 */
	public void openFile(File selectedFile) {		
		if(paneFiles!=null && selectedFile!=null){
			TabFile previousTab = getPreviousTab(selectedFile), tab;
			
			if(previousTab==null){
				//Create the tab and the container
				tab = new TabFile(selectedFile, updateNotification);
				tab.setOnChangeTabName(changeTabNameListener);
				paneFiles.getTabs().add(tab.getTab());
				//Add it to the map of files
				files.put(tab.getTab(), tab);
			}else{
				tab = previousTab;
			}		
			tab.openFile();
			paneFiles.getSelectionModel().select(tab.getTab());			
		}
	}

	/**
	 * Look for a tab which have in the content the selected file
	 * @param paneFiles2
	 * @param selectedFile
	 * @return
	 */
	private TabFile getPreviousTab(File selectedFile) {
		TabFile lookingFor = null;
		if(selectedFile!=null && paneFiles!=null && files!=null){
			Set<Tab> keys = files.keySet();
			for (Iterator<Tab> iterator = keys.iterator(); lookingFor==null && iterator.hasNext();) {
				Tab key = iterator.next();
				TabFile tabFile = files.get(key);
				if(selectedFile.equals(tabFile.getFile())){
					lookingFor = tabFile;
				}
			}
		}
		return lookingFor;
	}

	/**
	 * Creating a new file with an empty content
	 */
	public void createNewFile() {
		if(paneFiles!=null){
			//Create an empty tab
			String tabName = "New "+counter ++;
			TabFile tabFile = new TabFile(tabName, updateNotification);	
			tabFile.setOnChangeTabName(changeTabNameListener);
			paneFiles.getTabs().add(tabFile.getTab());
			paneFiles.getSelectionModel().select(tabFile.getTab());
		}		
	}

	/**
	 * Save the file currently selected
	 */
	public void saveFile() {
		TabFile tabFile = getTabFileSelected();
		tabFile.saveFile();		
	}

	/**
	 * Save the file as...
	 */
	public void saveAsFile() {
		TabFile tabFile = getTabFileSelected();
		tabFile.saveAsFile();
	}

	/**
	 * Get the file of the current tab
	 * @return
	 */
	private TabFile getTabFileSelected() {
		TabFile tabSelFile = null;
		Tab tab = paneFiles.getSelectionModel().getSelectedItem();
		if(tab!=null){
			tabSelFile = files.get(tab);
		}
		return tabSelFile;
	}

	public void closeCurrent() {
		Tab tab = paneFiles.getSelectionModel().getSelectedItem();
		if(tab!=null){
			files.remove(tab);
			paneFiles.getTabs().remove(tab);
		}
	}
}
