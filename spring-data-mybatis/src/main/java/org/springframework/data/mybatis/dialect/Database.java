/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.mybatis.dialect;

import java.lang.reflect.InvocationTargetException;

/**
 * List all supported relational database systems.
 *
 * @author JARVIS SONG
 * @since 2.0.0
 */
public enum Database {

	/**
	 * Derby.
	 */
	DERBY {
		@Override
		public Class<? extends Dialect> latestDialect() {
			return DerbyTenSevenDialect.class;
		}

		@Override
		public Dialect resolveDialect(DialectResolutionInfo info) {
			final String databaseName = info.getDatabaseName();

			if ("Apache Derby".equals(databaseName)) {
				final int majorVersion = info.getDatabaseMajorVersion();
				final int minorVersion = info.getDatabaseMinorVersion();

				if (majorVersion > 10 || (majorVersion == 10 && minorVersion >= 7)) {
					return latestDialectInstance(this);
				}
				else if (majorVersion == 10 && minorVersion == 6) {
					return new DerbyTenSixDialect();
				}
				else if (majorVersion == 10 && minorVersion == 5) {
					return new DerbyTenFiveDialect();
				}
				else {
					return new DerbyDialect();
				}
			}
			return null;
		}
	},
	/**
	 * DB2.
	 */
	DB2 {
		@Override
		public Class<? extends Dialect> latestDialect() {
			return DB2400Dialect.class;
		}

		@Override
		public Dialect resolveDialect(DialectResolutionInfo info) {
			final String databaseName = info.getDatabaseName();

			if ("DB2 UDB for AS/400".equals(databaseName)) {
				return new DB2400Dialect();
			}

			if (databaseName.startsWith("DB2/")) {
				return new DB2Dialect();
			}

			return null;
		}
	},

	/**
	 * SQLite.
	 */
	SQLITE {
		@Override
		public Class<? extends Dialect> latestDialect() {
			return SQLiteDialect.class;
		}

		@Override
		public Dialect resolveDialect(DialectResolutionInfo info) {
			final String databaseName = info.getDatabaseName();
			if ("SQLite".equals(databaseName)) {
				return latestDialectInstance(this);
			}
			return null;
		}
	},

	/**
	 * DaMeng.
	 */
	DM {
		@Override
		public Class<? extends Dialect> latestDialect() {
			return DMDialect.class;
		}

		@Override
		public Dialect resolveDialect(DialectResolutionInfo info) {
			final String databaseName = info.getDatabaseName();
			if ("DM DBMS".equals(databaseName)) {
				return latestDialectInstance(this);
			}
			return null;
		}
	},

	/**
	 * EnterpriseDB.
	 */
	ENTERPRISEDB {
		@Override
		public Class<? extends Dialect> latestDialect() {
			return PostgresPlusDialect.class;
		}

		@Override
		public Dialect resolveDialect(DialectResolutionInfo info) {
			final String databaseName = info.getDatabaseName();

			if ("EnterpriseDB".equals(databaseName)) {
				return latestDialectInstance(this);
			}

			return null;
		}
	},
	/**
	 * Oracle.
	 */
	ORACLE {
		@Override
		public Class<? extends Dialect> latestDialect() {
			return Oracle12cDialect.class;
		}

		@Override
		public Dialect resolveDialect(DialectResolutionInfo info) {
			final String databaseName = info.getDatabaseName();
			if ("Oracle".equals(databaseName)) {
				final int majorVersion = info.getDatabaseMajorVersion();

				switch (majorVersion) {
				case 12:
					return new Oracle12cDialect();
				case 11:
				case 10:
					return new Oracle10gDialect();
				case 9:
					return new Oracle9iDialect();
				case 8:
					return new Oracle8iDialect();
				default:
					return latestDialectInstance(this);
				}
			}
			return null;
		}
	},
	/**
	 * Microsoft SQL Server.
	 */
	SQLSERVER {
		@Override
		public Class<? extends Dialect> latestDialect() {
			return SQLServer2012Dialect.class;
		}

		@Override
		public Dialect resolveDialect(DialectResolutionInfo info) {
			final String databaseName = info.getDatabaseName();

			if (databaseName.startsWith("Microsoft SQL Server")) {
				final int majorVersion = info.getDatabaseMajorVersion();

				switch (majorVersion) {
				case 8:
					return new SQLServerDialect();
				case 9:
				case 10:
					return new SQLServer2005Dialect();
				case 11:
				case 12:
				case 13:
					return new SQLServer2012Dialect();
				default:
					if (majorVersion < 8) {
						return new SQLServerDialect();
					}
					else {
						return latestDialectInstance(this);
					}
				}
			}
			return null;
		}
	},
	/**
	 * PostgreSQL.
	 */
	POSTGRESQL {
		@Override
		public Class<? extends Dialect> latestDialect() {
			return PostgreSQLDialect.class;
		}

		@Override
		public Dialect resolveDialect(DialectResolutionInfo info) {
			final String databaseName = info.getDatabaseName();

			if ("PostgreSQL".equals(databaseName)) {
				return latestDialectInstance(this);
			}
			return null;
		}
	},
	/**
	 * HSQLDB.
	 */
	HSQL {
		@Override
		public Class<? extends Dialect> latestDialect() {
			return HSQLDialect.class;
		}

		@Override
		public Dialect resolveDialect(DialectResolutionInfo info) {
			final String databaseName = info.getDatabaseName();

			if ("HSQL Database Engine".equals(databaseName)) {
				return latestDialectInstance(this);
			}

			return null;
		}
	},

	/**
	 * H2.
	 */
	H2 {
		@Override
		public Class<? extends Dialect> latestDialect() {
			return H2Dialect.class;
		}

		@Override
		public Dialect resolveDialect(DialectResolutionInfo info) {
			final String databaseName = info.getDatabaseName();

			if ("H2".equals(databaseName)) {
				return latestDialectInstance(this);
			}

			return null;
		}
	},
	/**
	 * MySQL.
	 */
	MYSQL {
		@Override
		public Class<? extends Dialect> latestDialect() {
			return MySQLDialect.class;
		}

		@Override
		public Dialect resolveDialect(DialectResolutionInfo info) {
			final String databaseName = info.getDatabaseName();

			if ("MySQL".equals(databaseName)) {
				return latestDialectInstance(this);
			}

			return null;
		}
	};

	public abstract Class<? extends Dialect> latestDialect();

	public abstract Dialect resolveDialect(DialectResolutionInfo info);

	private static Dialect latestDialectInstance(Database database) {
		try {
			return database.latestDialect().getDeclaredConstructor().newInstance();
		}
		catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
			throw new DialectException(ex.getMessage(), ex);
		}
	}

}
