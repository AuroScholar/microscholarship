package com.auro.scholr.home.domain.usecase;

import com.auro.scholr.home.data.model.AccountDataModel;

import java.util.ArrayList;
import java.util.List;

public class AccountUseCase {

    public List<AccountDataModel> makeAccountList() {
        List<AccountDataModel> itemModels=new ArrayList<>();
        itemModels.add(new AccountDataModel("My Order"));
        itemModels.add(new AccountDataModel("My Profile"));
        itemModels.add(new AccountDataModel("My Addresses"));
        itemModels.add(new AccountDataModel("My Wallet"));
        itemModels.add(new AccountDataModel("My Rewards"));
        itemModels.add(new AccountDataModel("My Looks"));
        itemModels.add(new AccountDataModel("My Review"));
        itemModels.add(new AccountDataModel("My Reservations"));

        return itemModels;
    }
}
