package controller;

import dao.AssetgroupFacade;
import entity.Assetgroup;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author woutbr@student.hik.be
 */
@Named(value = "assetgroupController")
@SessionScoped
public class AssetgroupController implements Serializable {

    @EJB
    private AssetgroupFacade assetgroupFacade;
    private Assetgroup assetgroup = null;

    public AssetgroupController() {
        this.resetAssetgroup();
    }

    public Assetgroup getAssetgroup() {
        return assetgroup;
    }

    public void setAssetgroup(Assetgroup assetgroup) {
        this.assetgroup = assetgroup;
    }

    public void onload() {
        this.resetAssetgroup();
    }
    private void resetAssetgroup() {
        this.setAssetgroup(new Assetgroup());
    }

    public List<Assetgroup> listAllAssetgroups() {
        //return this.assetgroupFacade.findAll();
        return this.assetgroupFacade.listAllAssetGroups();
    }
    
    public Assetgroup findByID(BigDecimal id){
        return this.assetgroupFacade.find(id);
    }
    
    public String createNewAssetgroup(){
        this.assetgroupFacade.edit(assetgroup);
        return "assetList?faces-redirect=true";
    }

}
