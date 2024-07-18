package com.uniovi.entities.keys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VotoKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "user_id")
	Long userId;

	@Column(name = "votacion_id")
	Long votacionId;

	public VotoKey() {
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getVotacionId() {
		return votacionId;
	}

	public void setVotacionId(Long votacionId) {
		this.votacionId = votacionId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((votacionId == null) ? 0 : votacionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VotoKey other = (VotoKey) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (votacionId == null) {
			if (other.votacionId != null)
				return false;
		} else if (!votacionId.equals(other.votacionId))
			return false;
		return true;
	}

}
