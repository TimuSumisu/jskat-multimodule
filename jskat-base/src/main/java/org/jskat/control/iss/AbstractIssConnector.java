/**
 * JSkat - A skat program written in Java
 * by Jan Schäfer, Markus J. Luzius and Daniel Loreck
 *
 * Version 0.13.0-SNAPSHOT
 * Copyright (C) 2013-05-10
 *
 * Licensed under the Apache License, Version 2.0. You may
 * obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jskat.control.iss;

import org.jskat.data.JSkatOptions;
import org.jskat.util.JSkatResourceBundle;

/**
 * Abstract implementation of {@link IssConnector}
 */
abstract class AbstractIssConnector implements IssConnector {

	protected static final JSkatResourceBundle strings = JSkatResourceBundle
			.instance();
	protected static final JSkatOptions options = JSkatOptions.instance();

	protected String loginName;
	protected String password;

	/**
	 * Sets login credentials
	 * 
	 * @param newLoginName
	 *            Login name
	 * @param newPassword
	 *            Password
	 */
	@Override
	public void setConnectionData(final String newLoginName,
			final String newPassword) {
		loginName = newLoginName;
		password = newPassword;
	}

}
