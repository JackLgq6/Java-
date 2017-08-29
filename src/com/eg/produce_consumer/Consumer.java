package com.eg.produce_consumer;
/**
 * 消费者Consumer类
 * @author gqliu
 *
 */
public class Consumer extends Thread {
	
	 // 每次消费的产品数量  
	private int num;
	
	// 所在放置的仓库
	private Storage storage;
	
	// 构造方法，设置仓库
	public Consumer(Storage storage) {
		this.storage = storage;
	}

	@Override
	public void run() {
		consume(num);
	}
	
	public void consume(int num) {
		storage.consume(num);
	}
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}
	
	

}
