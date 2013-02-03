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
 * File created: 02/02/2013 at 22:15:11 by antonio
 */
package com.iniesta.editorfx.editor.files;

import java.io.File;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TreeItem;

/**
 * @author antonio
 *
 */
public class ServiceFolderLoading extends Service<TreeItem<File>> {
	
	private File rootFolder;

	public ServiceFolderLoading(File rootFolder){
		this.rootFolder = rootFolder;
	}
	
	@Override
	protected Task<TreeItem<File>> createTask() {
		return new Task<TreeItem<File>>() {
			@Override
			protected TreeItem<File> call() throws Exception {
				TreeItem<File> root = null;
				if(rootFolder!=null && rootFolder.exists()){
					root = getTreeItem(rootFolder);					
				}				
				return root;
			}
		};
	}

	protected TreeItem<File> getTreeItem(File file){
		TreeItem<File> treeItem = null;
		if(file!=null){
			treeItem = new TreeItem<File>(file);
			if(file.isDirectory()){
				//Extract recursevely all the nodes
				File[] children = file.listFiles();
				if(children!=null){
					for (File kid : children) {
						treeItem.getChildren().add(getTreeItem(kid));
					}
				}
			}
		}
		return treeItem;
	}
}
