package com.yw.user.service;

import com.yw.user.common.dto.UserGoldCoinRecordDTO;
import com.yw.user.common.dto.score.UserScoreRecordDTO;
import com.yw.user.common.enums.AssetsChangeTypeEnum;
import com.yw.user.common.enums.UserResponseCode;
import com.yw.user.common.model.UserAssets;
import com.yw.user.common.request.GrantAssetsRequest;
import com.yw.user.common.vo.UserAssetsVO;
import com.yw.user.mapper.UserAssetsMapper;
import com.yw.user.struct.UserAssetsStruct;
import com.yw.user.struct.UserGoldCoinRecordStruct;
import com.yw.user.struct.UserScoreRecordStruct;
import com.yyw.api.exception.BusinessException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/26 17:08
 */
@Service
public class UserAssetsService {

    @Resource
    private UserAssetsMapper userassetsMapper;
    @Resource
    private UserGoldCoinRecordService userGoldCoinRecordService;
    @Resource
    private UserScoreRecordService userScoreRecordService;
    @Lazy
    @Resource
    private UserAssetsService proxyUserAssetsService;

    public UserAssets get(Long userId) {
        UserAssets userAssets = userassetsMapper.findByUserId(userId);
        return Optional.ofNullable(userAssets)
                .orElseThrow(() -> new BusinessException(UserResponseCode.USER_NOT_EXIST));
    }

    public UserAssetsVO getByUserId(Long userId) {
        UserAssets userAssets = get(userId);
        return UserAssetsStruct.INSTANCE.convert(userAssets);
    }

    public void grant(GrantAssetsRequest grantAssetsRequest) {
        if (exists(grantAssetsRequest)) {
            return;
        }
        UserAssets userAssets = get(grantAssetsRequest.getUserId());
        proxyUserAssetsService.doGrant(grantAssetsRequest, userAssets);
    }

    private boolean exists(GrantAssetsRequest grantAssetsRequest) {
        return userScoreRecordService.exists(grantAssetsRequest.getSerialNumber())
                || userGoldCoinRecordService.exists(grantAssetsRequest.getSerialNumber());
    }

    @Transactional(rollbackFor = Exception.class)
    public void doGrant(GrantAssetsRequest grantAssetsRequest, UserAssets userAssets) {
        long newScore = userAssets.getScore() + grantAssetsRequest.getScore();
        long newGoldCoins = userAssets.getGoldCoins() + grantAssetsRequest.getGoldCoins();

        addUserScoreRecord(grantAssetsRequest, newScore);
        addGoldCoinRecord(grantAssetsRequest, newGoldCoins);
        updateUserAssets(grantAssetsRequest.getUserId(), userAssets, newScore, newGoldCoins);
    }

    private void addUserScoreRecord(GrantAssetsRequest grantAssetsRequest, long newScore) {
        UserScoreRecordDTO userScoreRecordDTO = UserScoreRecordStruct.INSTANCE.convert(grantAssetsRequest, newScore, AssetsChangeTypeEnum.IN);
        userScoreRecordService.add(userScoreRecordDTO);
    }

    private void addGoldCoinRecord(GrantAssetsRequest grantAssetsRequest, long newGoldCoins) {
        UserGoldCoinRecordDTO userGoldCoinRecordDTO = UserGoldCoinRecordStruct.INSTANCE.convert(grantAssetsRequest, newGoldCoins, AssetsChangeTypeEnum.IN);
        userGoldCoinRecordService.add(userGoldCoinRecordDTO);
    }

    private void updateUserAssets(Long userId, UserAssets userAssets, long newScore, long newGoldCoins) {
        userassetsMapper.changeScoreAndGoldCoin(userId, newScore, newGoldCoins, userAssets.getModifyDate());
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchGrant(List<GrantAssetsRequest> grantAssetsRequests) {
        grantAssetsRequests.forEach(this::grant);
    }


}
