class MyTestController < ApplicationController
  def index
    render_text "Hello world"
  end

  def dilbert
    render_text "Wow, that was easy"
  end
end
