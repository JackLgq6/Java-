package com.eg.pro_con;

import java.util.concurrent.LinkedBlockingQueue;
/**
 * 仓库类Storage实现缓冲区
 * @author gqliu
 *
 */
public class Storage {

	private final int MAX_SIZE = 100;
	
	private LinkedBlockingQueue<Object> list = new LinkedBlockingQueue<Object>(100);
	
	public void produce(int num) {
		// 如果仓库剩余容量为0
		if (list.size() == MAX_SIZE) {
			System.out.println("库存量：" + MAX_SIZE + ",暂时不能执行生产任务");
		}
		// 生产条件满足情况下，生产num个产品  
		for (int i = 0; i < num; i++) {
			try {
				// 放入产品，自动阻塞  
				list.put(new Object());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("现仓存储量：" + list.size());
	}
	
	public void consume(int num) {
		if (list.size() == 0) {
			System.out.println("库存量0，暂时不能执行消费任务");
		}
		// 消费条件满足情况下，消费num个产品 
		for (int i = 0; i < num; i++) {
			try {
				// 消费产品，自动阻塞  
				list.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("现仓存储量：" + list.size());
	}
	
	// set/get方法  
    public LinkedBlockingQueue<Object> getList() {  
        return list;  
    }  
  
    public void setList(LinkedBlockingQueue<Object> list) {  
        this.list = list;  
    }  
  
    public int getMAX_SIZE() {  
        return MAX_SIZE;  
    }  
}
