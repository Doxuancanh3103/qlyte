package vt.qlkdtt.yte.service;

import vt.qlkdtt.yte.service.sdi.DoConnectSdi;
import vt.qlkdtt.yte.service.sdo.DoConnectSdo;

public interface DoConnectService {
    DoConnectSdo doConnect(DoConnectSdi dataConnect);
}
