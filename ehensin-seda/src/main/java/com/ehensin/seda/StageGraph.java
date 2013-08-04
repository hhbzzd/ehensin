/*
 * Copyright 2013 The Ehensin SEDA Project
 *
 * The Ehensin SEDA Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.ehensin.seda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ehensin.seda.spi.IStageContext;


public class StageGraph {
	
    private Map<String, StageEdge> stageEdageMap;
    
    private List<StageEdge> roots;
    public StageGraph(){
    	stageEdageMap = new HashMap<String, StageEdge>();
    	roots = new ArrayList<StageEdge>();
    }
    public List<StageEdge> getRoots(){
    	return roots;
    }
  
    public boolean addEdge(IStageContext stageCtx){
    	if( stageEdageMap.get(stageCtx.getStageName()) != null ){
    		return false;
    	}
    	StageEdge edge = new StageEdge(null, null,stageCtx);
    	roots.add(edge);
    	stageEdageMap.put(stageCtx.getStageName(), edge);
    	return true;
    }
   
    public boolean append(IStageContext frontStage, IStageContext stageCtx){
    	if( stageEdageMap.get(stageCtx.getStageName()) != null ){
    		return false;
    	}
    	StageEdge front = stageEdageMap.get(frontStage.getStageName());
    	if( front != null ){
    		StageEdge current = new StageEdge(null, front, stageCtx);
    		front.addNext(current);
    		stageEdageMap.put(stageCtx.getStageName(), current);
    	}
    	
    	return true;
    }
    
    public StageEdge lookup(String stageName){
    	for( StageEdge edge : roots ){
    		StageEdge e = recursive(edge, stageName); 
    		if( e != null ){
    			return e;
    		}
    	}
    	return null;
    }

    private StageEdge recursive(StageEdge edge, String stageName){
		if(edge.getStageCtx().getStageName().equals(stageName) )
			return edge;
		else{
			List<StageEdge> list = edge.getNexts();
			if( list != null ){
				for( StageEdge e : list ){
					return recursive(e, stageName);
				}
			}
		}
		return null;
   }
 
}
