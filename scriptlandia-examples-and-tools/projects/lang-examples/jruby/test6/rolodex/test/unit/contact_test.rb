require File.dirname(__FILE__) + '/../test_helper'

class ContactTest < Test::Unit::TestCase
  fixtures :contacts

  # Replace this with your real tests.
  def test_truth
    assert_kind_of Contact, contacts(:first)
  end
end
