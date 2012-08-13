package com.hx.model.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "hx_combats", catalog = "hx")
public class Combat {

	@Id
	@Column(name = "combat_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer combatId;

	@ManyToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "combat_sector", referencedColumnName = "sector_id")
	private Sector sector;

	@ManyToMany(targetEntity = com.hx.model.dto.Regiment.class, cascade = {
			CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
	@JoinTable(schema = "hx", name = "hx_combat_defender", joinColumns = @JoinColumn(name = "combat_id"), inverseJoinColumns = @JoinColumn(name = "regiment_id"))
	private List<Regiment> defenderRegiments;

	@ManyToMany(targetEntity = com.hx.model.dto.Personality.class, cascade = {
			CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
	@JoinTable(schema = "hx", name = "hx_combat_attacker", joinColumns = @JoinColumn(name = "combat_id"), inverseJoinColumns = @JoinColumn(name = "regiment_id"))
	private List<Regiment> attackerRegiments;

	@Column(name = "pending")
	private Boolean pending;

	public Integer getCombatId() {
		return combatId;
	}

	public void setCombatId(Integer combatId) {
		this.combatId = combatId;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public List<Regiment> getDefenderRegiments() {
		return defenderRegiments;
	}

	public void setDefenderRegiments(List<Regiment> defenderRegiments) {
		this.defenderRegiments = defenderRegiments;
	}

	public List<Regiment> getAttackerRegiments() {
		return attackerRegiments;
	}

	public void setAttackerRegiments(List<Regiment> attackerRegiments) {
		this.attackerRegiments = attackerRegiments;
	}

	public Boolean getPending() {
		return pending;
	}

	public void setPending(Boolean pending) {
		this.pending = pending;
	}

}
