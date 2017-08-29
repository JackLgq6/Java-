package com.eg.produce_consumer;

import java.util.LinkedList;
/**
 * 仓库类Storage实现缓冲区
 * @author gqliu
 *
 */

public class Storage {

	//仓库最大存储量
	private final int MAX_SIZE = 100;
	
	//仓库载体
	private LinkedList<Object> list = new LinkedList<Object>();
	
	//生产num个商品
	public void produce(int num) {
		//同步代码块
		synchronized (list) {
			//由于条件不满足，生产阻塞  
			while (list.size() + num > MAX_SIZE) {
				System.out.println("要生产的数量：" + num + ", 库存量：" + list.size()
					+ ",暂时不能执行生产任务");
				try {
					// 由于条件不满足，生产阻塞  
					list.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// 生产条件满足情况下，生产num个产品  
			for (int i = 0; i < num; i++) {
				Object object = new Object();
				list.add(object);
			}
			
			System.out.println("已生产产品数：" + num + ", 现仓存储量：" + list.size());
			list.notifyAll();
		}
	}
	
	// 消费num个产品  
	public void consume(int num) {
		//同步代码块
		synchronized (list) {
			// 如果仓库存储量不足  
			while (list.size() < num) {
				System.out.println("要消费的数量：" + num + ", 库存量：" + list.size() + ", 暂时不能执行消费任务");
				try {
					// 由于条件不满足，消费阻塞  
					list.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// 消费条件满足情况下，消费num个产品 
			for (int i = 0; i < num; i++) {
				list.remove();
			}
			System.out.println("已经消费产品数：" + num + "，现仓存储量：" + list.size());
			list.notifyAll();
		}
	}
	
	// get/set方法  
    public LinkedList<Object> getList() {  
        return list;  
    }  
  
    public void setList(LinkedList<Object> list) {  
        this.list = list;  
    }  
  
    public int getMAX_SIZE() {  
        return MAX_SIZE;  
    }  
}