<?php
	class DBConnect
	{
		private $con;
		function __construct()
		{

		}

		function connect()
		{
			include_once dirname(__FILE__).'/Constants.php';
			$this->con = new mysqli(Database_host,Database_user,Database_password,Database_name); 
			if(mysqli_connect_errno())
				echo "Failed to connect with database".mysqli_connect_err();
			return $this->con;
		}
	}