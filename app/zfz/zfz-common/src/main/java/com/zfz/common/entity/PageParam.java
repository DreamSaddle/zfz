package com.zfz.common.entity;


/**
 * author: DreamSaddle
 * date: 2020年01月04日
 * time: 15:24
 */
public class PageParam {

	private int page = 1;

	private int size = 10;

	public void setPage(final int page) {
		this.page = page;
	}

	public void setSize(final int size) {
		this.size = size;
	}

	public int getPage() {
		return page;
	}

	public int getSize() {
		return size;
	}
}
