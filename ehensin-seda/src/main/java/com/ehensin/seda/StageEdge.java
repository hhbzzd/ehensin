package com.ehensin.seda;

import java.util.ArrayList;
import java.util.List;

import com.ehensin.seda.spi.IStageContext;


class StageEdge {
	


	private IStageContext stageCtx;

    private List<StageEdge> nexts;

    private StageEdge prev;
    
    public StageEdge(List<StageEdge> nexts, StageEdge prev, IStageContext stageCtx){
    	this.nexts = nexts;
    	this.prev = prev;
    	this.stageCtx = stageCtx;
    }
	public List<StageEdge> getNexts() {
		return nexts;
	}
	public void addNext(StageEdge next) {
		if(this.nexts == null){
			nexts = new ArrayList<StageEdge>();
		}
		nexts.add(next);
	}

	public StageEdge getPrev() {
		return prev;
	}
	public void setPrev(StageEdge prev) {
		this.prev = prev;
	}

	public IStageContext getStageCtx() {
		return stageCtx;
	}

    
    
}
