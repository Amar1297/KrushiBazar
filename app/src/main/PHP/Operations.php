<?php
	class DBOperations()
	{
		private $con;
		function __construct()
		{
			require_once dirname(__FILE__).'/Connection.php';
			$db = new Connection();
			$this->con = $db->connect();
		}

		function create_user($username, $email, $pass)
		{
			$password = md5($pass);
			$stmt = this->con->prepare("insert into 'products'('name','desc','price','status') values(?,?,?,?);");
			$stmt->bind_param("sss", $username, $password, $email);
			if($stmt->execute())
				return true;
			else
				return false;
		}
	}