package com.eg.produce_consume_demo2;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
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
	
	//锁
	private final Lock lock = new ReentrantLock();
	 // 仓库满的条件变量  
	private final Condition full = lock.newCondition();
	//仓库空的条件变量
	private final Condition empty = lock.newCondition();
	
      
	
	//生产num个商品
	public void produce(int num) {
		//获得锁
		lock.lock();
		//由于条件不满足，生产阻塞  
		while (list.size() + num > MAX_SIZE) {
			System.out.println("要生产的数量：" + num + ", 库存量：" + list.size()
				+ ",暂时不能执行生产任务");
			try {
				// 由于条件不满足，生产阻塞  
				full.await();
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
		// 唤醒其他所有线程  
		full.signalAll();
		empty.signalAll();
		//释放锁
		lock.unlock();
	}
	
	// 消费num个产品  
	public void consume(int num) {
		//获得锁
		lock.lock();
		// 如果仓库存储量不足  
		while (list.size() < num) {
			System.out.println("要消费的数量：" + num + ", 库存量：" + list.size() + ", 暂时不能执行消费任务");
			try {
				// 由于条件不满足，消费阻塞  
				empty.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// 消费条件满足情况下，消费num个产品 
		for (int i = 0; i < num; i++) {
			list.remove();
		}
		System.out.println("已经消费产品数：" + num + "，现仓存储量：" + list.size());
		// 唤醒其他所有线程  
		full.signalAll();
		empty.signalAll();
		//释放锁
		lock.unlock();
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
