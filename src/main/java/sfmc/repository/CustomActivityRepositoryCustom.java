package sfmc.repository;

import sfmc.model.CustomActivity.CustomActivityConfig;

public interface CustomActivityRepositoryCustom {

    public String getRestConfig(CustomActivityConfig config);

    public String getSplitConfig(CustomActivityConfig config);

    public String getSplitResult();
}
