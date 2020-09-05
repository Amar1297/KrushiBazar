<?php
	require_once '../includes/Operations.php';
	$response = array();
	if($_SERVER['REQUEST_METHOD']=='POST')
	{
		if(isset($_POST['username']) and isset($_POST['email']) and isset($_POST['password']))
		{
			$db = new Operations();
			if($db->createuser($_POST['username'],$_POST['password'],$_POST['email']));
			{
				$response['error'] = false;
				$response['message'] = "User registered"
			}
			else
			{
				$response['error'] = true;
				$response['message'] = "Error occured"	
			}
		}
		else
		{
			$response['error'] = true;
			$response['message'] = "Reauired fields are missing";
		}
	}else
	{
		$response['error'] = true;
		$response['message'] = "Invalid Request"
	}

	echo json_encode($response);