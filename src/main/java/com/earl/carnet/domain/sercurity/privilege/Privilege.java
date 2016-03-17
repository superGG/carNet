package com.earl.carnet.domain.sercurity.privilege;

import java.io.Serializable;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

import com.earl.carnet.commons.domain.AbstractAuditingEntity;

@Table(name = "privilege")
public class Privilege extends AbstractAuditingEntity<Long> implements Serializable {
	
    private String describ;

    private String privilegeName;
    
    /**
     * 权限编号. user:*,user:delete,user:create,user:update
     */
    private String privilegeCode;
    
    private String matchUrl;

    private static final long serialVersionUID = 1L;

    @AutoID
    public Long getId() {
        return super.getId();
    }
    
    public void setId(Long id) {
    	super.setId(id);
    }

	public String getPrivilegeCode() {
		return privilegeCode;
	}

	public void setPrivilegeCode(String privilegeCode) {
		this.privilegeCode = privilegeCode;
	}

	public String getDescrib() {
		return describ;
	}

	public void setDescrib(String describ) {
		this.describ = describ;
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	public String getMatchUrl() {
		return matchUrl;
	}

	public void setMatchUrl(String matchUrl) {
		this.matchUrl = matchUrl;
	}

	@Override
	public String toString() {
		return "Privilege [describ=" + describ + ", privilegeName="
				+ privilegeName + ", matchUrl=" + matchUrl + "]";
	}

}