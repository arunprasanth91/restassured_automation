Feature: Validating place API's

  @AddPlace
  Scenario Outline: Verify if place is being successfully added using AddPlaceAPI
    Given Add place payload with values "<Address>" "<name>" "<phone_number>" "<website>" "<language>"
    When user calls "AddPlaceAPI" with "post" http request
    Then the API call is success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify place_Id created maps to "<name>" using "GetPlaceAPI"
    Examples:
      | Address                       | name               | phone_number       | website            | language   |
      | 29, side layout, cohen 091232 | Frontline house123 | (+65) 983 893 3937 | https://google.com | German-UK  |
      | 13, Park gate, India 600088   | MG road            | (+91) 123 893 3937 | https://yahoo.com  | English-US |

  @DeletePlace
  Scenario: Verify if Delete place functionality is working
    Given DeletePlace payload
    When user calls "DeletePlaceAPI" with "post" http request
    Then the API call is success with status code 200


