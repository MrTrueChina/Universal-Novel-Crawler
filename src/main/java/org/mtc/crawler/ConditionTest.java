package org.mtc.crawler;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 	Condition的测试类
 */
public class ConditionTest {
	public static void main(String[] args) throws InterruptedException {

		Lock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		Condition conditionO = lock.newCondition();

		ConditionTestP p = new ConditionTestP(lock, condition);
		ConditionTestC c = new ConditionTestC(lock, condition);
		ConditionTestO o = new ConditionTestO(lock, conditionO);

		p.start();
		o.start();
		Thread.sleep(100);
		c.start();
	}
}

class ConditionTestP extends Thread {

	private Lock _lock;
	private Condition _condition;

	public ConditionTestP(Lock lock, Condition condition) {
		_lock = lock;
		_condition = condition;
	}

	@Override
	public void run() {
		try {
			_lock.lock();

			while (true) {
				_condition.await();
				System.out.println("P，UP");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			_lock.unlock();
		}
	}
}

class ConditionTestC extends Thread {

	private Lock _lock;
	private Condition _condition;

	public ConditionTestC(Lock lock, Condition condition) {
		_lock = lock;
		_condition = condition;
	}

	@Override
	public void run() {
		while (true) {
			_lock.lock();
			_condition.signalAll();
			_lock.unlock();
		}
	}
}

class ConditionTestO extends Thread{

	private Lock _lock;
	private Condition _condition;

	public ConditionTestO(Lock lock, Condition condition) {
		_lock = lock;
		_condition = condition;
	}

	@Override
	public void run() {
		try {
			_lock.lock();

			while (true) {
				_condition.await();
				System.out.println("O，UP");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			_lock.unlock();
		}
	}
}