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
 * File created: 15/02/2013 at 00:05:09 by antonio
 */
package com.iniesta.editorfx.editor;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

/**
 * @author antonio
 *
 */
public class UpdateNotification {

	private ProgressIndicator progressIndicator;
	private Label labelStatus;
	
	private BooleanBinding orBinding = new SimpleBooleanProperty(true).not();
	
	/**
	 * @return the progressIndicator
	 */
	public ProgressIndicator getProgressIndicator() {
		return progressIndicator;
	}
	
	public void bind(ObservableBooleanValue boolProp){
		orBinding = orBinding.or(boolProp);
		progressIndicator.visibleProperty().bind(orBinding);
	}
	/**
	 * @param progressIndicator the progressIndicator to set
	 */
	public void setProgressIndicator(ProgressIndicator progressIndicator) {
		this.progressIndicator = progressIndicator;
	}
	/**
	 * @return the labelStatus
	 */
	public Label getLabelStatus() {
		return labelStatus;
	}
	/**
	 * @param labelStatus the labelStatus to set
	 */
	public void setLabelStatus(Label labelStatus) {
		this.labelStatus = labelStatus;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((labelStatus == null) ? 0 : labelStatus.hashCode());
		result = prime
				* result
				+ ((progressIndicator == null) ? 0 : progressIndicator
						.hashCode());
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
		if (!(obj instanceof UpdateNotification))
			return false;
		UpdateNotification other = (UpdateNotification) obj;
		if (labelStatus == null) {
			if (other.labelStatus != null)
				return false;
		} else if (!labelStatus.equals(other.labelStatus))
			return false;
		if (progressIndicator == null) {
			if (other.progressIndicator != null)
				return false;
		} else if (!progressIndicator.equals(other.progressIndicator))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UpdateNotification [progressIndicator=" + progressIndicator
				+ ", labelStatus=" + labelStatus + "]";
	}
	
}
