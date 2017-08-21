package sfdc.mc.repository;

import sfdc.mc.model.CustomActivityConfig;

public interface CustomActivityRepositoryCustom {

    public String getRestConfig(CustomActivityConfig config);

    public String getSplitConfig(CustomActivityConfig config);

    public String getSplitResult();
}
