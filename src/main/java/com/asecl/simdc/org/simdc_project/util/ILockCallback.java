package com.asecl.simdc.org.simdc_project.util;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface ILockCallback<T> {
    T exec() throws IOException, JAXBException;
}
