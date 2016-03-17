package com.earl.carnet.domain.sercurity.role;

import java.io.Serializable;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

import com.earl.carnet.commons.domain.AbstractAuditingEntity;

@Table(name = "role")
public class Role extends AbstractAuditingEntity<Long> implements Serializable {
	
    private String describ;

    private String roleName;
    
    private static final long serialVersionUID = 1L;

    @AutoID
    public Long getId() {
        return super.getId();
    }
    
    public void setId(Long id) {
    	super.setId(id);
    }

	public String getDescrib() {
		return describ;
	}

	public void setDescrib(String describ) {
		this.describ = describ;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "Role [describ=" + describ + ", roleName=" + roleName + "]";
	}

}