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
 * File created: 07/02/2013 at 20:43:08 by antonio
 */
package com.iniesta.editorfx.editor.files.container;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

/**
 * @author antonio
 *
 */
public class TextAreaContainer implements Container{

	private TextArea container;
	private SimpleBooleanProperty changed;
	
	public TextAreaContainer(){
		container = new TextArea();
		changed = new SimpleBooleanProperty(false);
		
		container.setOnKeyPressed(new EventHandler<Event>() {
			public void handle(Event arg0) {
				changed.set(true);
			}
		});
	}

	public StringProperty textProperty() {
		return container.textProperty();
	}

	public BooleanProperty changedProperty() {
		return changed;
	}

	public String getText() {		
		return container.getText();
	}

	public boolean isChanged() {
		return changed.get();
	}

	public Node getNode() {
		return container;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((changed == null) ? 0 : changed.hashCode());
		result = prime * result
				+ ((container == null) ? 0 : container.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TextAreaContainer))
			return false;
		TextAreaContainer other = (TextAreaContainer) obj;
		if (changed == null) {
			if (other.changed != null)
				return false;
		} else if (!changed.equals(other.changed))
			return false;
		if (container == null) {
			if (other.container != null)
				return false;
		} else if (!container.equals(other.container))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TextAreaContainer [container=" + container + ", changed="
				+ changed + "]";
	}
}
