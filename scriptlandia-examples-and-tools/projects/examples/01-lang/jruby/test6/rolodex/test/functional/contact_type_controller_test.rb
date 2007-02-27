require File.dirname(__FILE__) + '/../test_helper'
require 'contact_type_controller'

# Re-raise errors caught by the controller.
class ContactTypeController; def rescue_action(e) raise e end; end

class ContactTypeControllerTest < Test::Unit::TestCase
  def setup
    @controller = ContactTypeController.new
    @request    = ActionController::TestRequest.new
    @response   = ActionController::TestResponse.new
  end

  # Replace this with your real tests.
  def test_truth
    assert true
  end
end
