/**
 * 
 */
package com.wxx.conference.common;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.Entity;
import java.io.Serializable;


/**
 * @author wxx
 * @date 2016年11月30日 上午10:38:52
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE) // 使用默认大小写属性转换json
@Entity
public class Page implements Serializable {

	public Page() {
		super();
	}

	private int currIndex;

	private int count;

	public int getPrevIndex() {
		return currIndex - 1;
	}

	public int getCurrIndex() {
		return currIndex;
	}

	public void setCurrIndex(int currIndex) {
		this.currIndex = currIndex;
	}

	public int getNextIndex() {
		return currIndex + 1;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Page [currIndex=" + currIndex + ", prevIndex=" + this.getPrevIndex() + ", nextIndex="
				+ this.getNextIndex() + ", count=" + count + "]";
	}

}
