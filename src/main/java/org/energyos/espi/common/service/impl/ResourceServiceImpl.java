/*
 * Copyright 2013, 2014 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.energyos.espi.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.domain.Linkable;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.ResourceRepository;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceRepository repository;

    @Override
    public void persist(IdentifiedObject resource) {
        repository.persist(resource);
    }

    @Override
    public List<IdentifiedObject> findByAllParentsHref(String relatedHref, Linkable linkable) {
        try {

            if (linkable instanceof UsagePoint){
                return new ArrayList<>();
            } else {
                return repository.findAllParentsByRelatedHref(relatedHref, linkable);
            }
        } catch (Exception e) {

            return new ArrayList<>();
        }
    }

    @Override
    public List<IdentifiedObject> findAllRelated(Linkable linkable) {
    	List <IdentifiedObject> temp = repository.findAllRelated(linkable);
        return temp;
    }

    @Override
    public <T> T findByUUID(UUID uuid, Class<T> clazz) {
        return repository.findByUUID(uuid, clazz);
    }

	@Override
    @Transactional ( readOnly = true)  
	public <T extends IdentifiedObject> T testById(Long id, Class<T> clazz) {
		return repository.findById(id, clazz);
	}
	
    @Override
    public <T extends IdentifiedObject> T findById(Long id, Class<T> clazz) {
        return repository.findById(id, clazz);
    }

    @Override
    public <T extends IdentifiedObject> List<Long> findAllIds(Class<T> clazz) {
        return repository.findAllIds(clazz);
    }

    @Override
    public <T extends IdentifiedObject> List<Long> findAllIdsByUsagePointId(Long id, Class<T> clazz) {
        return repository.findAllIdsByUsagePointId(id, clazz);
    }

    // XPath Accessors
    //
    
	@Override
	public <T extends IdentifiedObject> List<Long> findAllIdsByXPath(Class<T> clazz) {
		return repository.findAllIdsByXPath(clazz);
	}
	
	@Override
	public <T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1,
			Class<T> clazz) {
		return repository.findAllIdsByXPath(id1, clazz);
	}

	@Override
	public <T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1,
			Long id2, Class<T> clazz) {
		return repository.findAllIdsByXPath(id1, id2, clazz);
	}

	@Override
	public <T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1,
			Long id2, Long id3, Class<T> clazz) {
		return repository.findAllIdsByXPath(id1, id2, id3, clazz);
	}
	
	@Override
	public <T extends IdentifiedObject> Long findIdByXPath(Long id1,
			Class<T> clazz) {
		return repository.findIdByXPath(id1, clazz);
	}

	@Override
	public <T extends IdentifiedObject> Long findIdByXPath(Long id1, Long id2,
			Class<T> clazz) {
		return repository.findIdByXPath(id1, id2, clazz);
	}

	@Override
	public <T extends IdentifiedObject> Long findIdByXPath(Long id1, Long id2,
			Long id3, Class<T> clazz) {
		return repository.findIdByXPath(id1, id2, id3, clazz);
	}

	@Override
	public <T extends IdentifiedObject> Long findIdByXPath(Long id1, Long id2,
			Long id3, Long id4, Class<T> clazz) {
		return repository.findIdByXPath(id1, id2, id3, id4, clazz);
	}
	
    public void setRepository(ResourceRepository repository) {
        this.repository = repository;
    }

	@Override
	public <T extends IdentifiedObject> EntryTypeIterator findEntryTypeIterator(Class<T> clazz) {
		List<Long> idList = repository.findAllIds(clazz);
        return findEntryTypeIterator(idList, clazz);
	}

	@Override
	public <T extends IdentifiedObject> EntryTypeIterator findEntryTypeIterator(List<Long> ids, Class<T> clazz) {
		List<Long> idList = ids;
		EntryTypeIterator result = null;
		try {
			result = (new EntryTypeIterator(this, idList, clazz));
		} catch (Exception e) {
			result = null;
		}
		return result;
	}
	
	@Override
	public <T extends IdentifiedObject> EntryType findEntryType(long id1, Class<T> clazz) {
		EntryType result = null;
		try {
			List<Long> temp = new ArrayList<Long>();
			temp.add(id1);
			result = (new EntryTypeIterator(this, temp, clazz)).nextEntry(clazz);
		} catch (Exception e) {
			result = null;
		}
		return result;
	}

	@Override
	public <T extends IdentifiedObject> T findByResourceUri(String uri,
			Class<T> clazz) {
		return repository.findByResourceUri(uri, clazz);
	}

	@Override
	public void flush() {
		
		repository.flush();	
	}

	@Override
	public <T extends IdentifiedObject> void deleteById(Long id, Class<T> clazz) {
		
		   repository.deleteById(id, clazz);
	}
}
