package com.example.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Created by pyshankov on 05.07.16.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractOptimisticLockEntity<ID> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id", unique = true, nullable = false)
    protected long id;

    @Version
    protected Integer version;

    @Transient
    protected Integer lastVisibleVersion;

    @PostLoad
    private void postLoad(){
        lastVisibleVersion=this.version;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getLastVisibleVersion() {
        return lastVisibleVersion;
    }

    public void setLastVisibleVersion(Integer lastVisibleVersion) {
        this.lastVisibleVersion = lastVisibleVersion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    @PreUpdate
//    private void prePersist() throws NoSuchFieldException, IllegalAccessException {
//        Field[] fields = this.getClass().getDeclaredFields();
//            for (Field f : fields ){
//                f.setAccessible(true);
//                if(f.get(this) instanceof Iterable ){
//                        Iterable iterable = (Iterable) f.get(this);
//                        iterable.forEach(p -> {
//                            if(p instanceof AbstractOptimisticLockEntity) {
//                               AbstractOptimisticLockEntity ap= ((AbstractOptimisticLockEntity) p);
//                                ap.setVersion(this.version+1);
//                            }
//                        });
//                }
//            }
//    }


}
