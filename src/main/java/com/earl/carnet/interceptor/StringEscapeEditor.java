package com.earl.carnet.interceptor;

import java.beans.PropertyEditorSupport;

public class StringEscapeEditor extends PropertyEditorSupport {
//	private boolean escapeHTML;
//	private boolean escapeJavaScript;
//	private boolean escapeSQL;

	public StringEscapeEditor() {
		super();
	}

	public StringEscapeEditor(boolean escapeHTML, boolean escapeJavaScript,
			boolean escapeSQL) {
		super();
//		this.escapeHTML = escapeHTML;
//		this.escapeJavaScript = escapeJavaScript;
//		this.escapeSQL = escapeSQL;
	}

	@Override
	public void setAsText(String text) {
		if (text == null) {
			setValue(null);
		} else {
//				text = new String(text.getBytes("UTF-8"));
			String value = text;
			value = text.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
			setValue(value);
		}
	}

	@Override
	public String getAsText() {
		Object value = getValue();
		return value != null ? value.toString() : "";
	}
}