package it.uniba.file.zip;

public final class NotValidWorkspaceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8654253055564465806L;
	private String message = " is not a valid Slack workspace";

	public NotValidWorkspaceException(final String file) {
		message = file + message;
	}

	public String getMessage() {
		return this.message;
	}
}
