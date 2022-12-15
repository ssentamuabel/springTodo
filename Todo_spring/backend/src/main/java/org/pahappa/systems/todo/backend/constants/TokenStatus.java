package org.pahappa.systems.todo.backend.constants;

public enum TokenStatus {

	/**
	 * All newly created tokens will be active
	 */
    ACTIVE("Active"),
    
    /**
     * When a new token is created for a user, all the old tokens for that user will be marked as expired
     */
    EXPIRED("Expired");

    private String name;

    TokenStatus(String name) {
        this.name = name;
    }

	public static TokenStatus toTokenStatus(String tokenStatus){
		if(tokenStatus == null || tokenStatus.isEmpty())
			return null;
		
		for(TokenStatus status : TokenStatus.values()){
			if(status.getName().equals(tokenStatus))
				return status;
		}
		return null;
	}
	
    public String getName() {
        return name;
    }
    
    public String setName(String name) {
		return name;
	}

    @Override
    public String toString() {
        return this.name;
    }
}
