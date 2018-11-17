package controller;

import dao.AssetFacade;
import entity.Asset;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Model Managed-Bean Distinctions between different kinds of JSF managed beans:
 * https://stackoverflow.com/a/1030196
 *
 * @author woutbr@student.hik.be
 */
@Named(value = "assetController")
@SessionScoped
public class AssetController implements Serializable {

    @EJB
    private AssetFacade assetFacade;
    private Asset asset = null;
    private BigDecimal assetidSelected = null;
    private boolean editMode = false;

    public AssetController() {
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public List<Asset> getAllAssets() {
        return this.assetFacade.findAll();
    }

    public Asset findByID(BigDecimal id) {
        return this.assetFacade.find(id);
    }

    public void loadPage() {
        this.assetidSelected = null;
        this.editMode = false;
    }

    public void setEditRow(Asset a) {
        this.asset = a;
        this.assetidSelected = a.getAssetid();
        this.editMode = true;
    }

    public void cancelEditRow() {
        this.asset = null;
        this.assetidSelected = null;
        this.editMode = false;
    }

    public boolean isRowEditable(Asset a) {
        return assetidSelected != null && assetidSelected == a.getAssetid() && this.editMode;
    }
    
    public boolean isEditMode(){
        return this.editMode;
    }

    public void saveAsset() {
        this.assetFacade.edit(asset);
        this.cancelEditRow();
    }
}
